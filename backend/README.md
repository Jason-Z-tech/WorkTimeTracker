# WorkTime Tracker

## Projektbeschreibung

Der **WorkTime Tracker** ist eine Backend-Applikation zur Erfassung und Verwaltung von Arbeitszeiten.

Benutzer können Projekten zugeordnet werden und für diese Projekte Zeiteinträge erstellen.  
Die Applikation stellt REST-Endpunkte zur Verfügung, mit denen Benutzer, Projekte, Zeiteinträge und Berichte verwaltet werden können.

Das Projekt wurde mit **Spring Boot** umgesetzt und verwendet **PostgreSQL** als relationale Datenbank.  
Die Authentifizierung und Autorisierung erfolgt über **Keycloak** mit **Bearer Token**.

Ziel des Projekts ist es, ein strukturiertes, wartbares und abgesichertes Backend zu realisieren, welches die Anforderungen des Moduls 295 erfüllt.

---

## Verwendete Technologien

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Keycloak
- OAuth2 Resource Server
- Swagger / OpenAPI
- Maven
- JUnit
- Mockito
- Postman
- Git / GitHub

---

## Projektstruktur

Das Projekt verwendet folgende Basis-Package-Struktur:

```text
ch.jason.zeitz.worktime.tracker
```

Wichtige Bereiche:

```text
config        Konfigurationen für Security, Keycloak-Rollen und OpenAPI
controller    REST-Controller für die API-Endpunkte
entity        Datenbank-Entitäten
repository    JPA-Repositories für den Datenbankzugriff
service       Business-Logik und Datenbankzugriff
```

Die Applikation ist nach einer klassischen 3-Schichten-Architektur aufgebaut:

```text
Controller-Schicht   Verarbeitung der HTTP-Anfragen
Service-Schicht      Geschäftslogik
Repository-Schicht   Zugriff auf die Datenbank
```

Diese Struktur sorgt für eine klare Trennung der Verantwortlichkeiten und verbessert die Wartbarkeit des Codes.

---

## Entitäten

### AppUser

Repräsentiert einen Benutzer der Applikation.

Wichtige Felder:

```text
- id
- username
- email
```

Validierung:

```text
- username darf nicht leer sein
- email darf nicht leer sein
- email muss ein gültiges E-Mail-Format haben
```

---

### Project

Repräsentiert ein Projekt, für welches Arbeitszeit erfasst werden kann.

Wichtige Felder:

```text
- id
- name
- description
- active
```

Validierung:

```text
- name darf nicht leer sein
```

---

### TimeEntry

Repraesentiert einen Zeiteintrag eines Benutzers zu einem Projekt.

Wichtige Felder:

```text
- id
- startTime
- endTime
- durationMinutes
- user
- project
```

Validierung:

```text
- startTime darf nicht leer sein
- endTime darf nicht leer sein
- user darf nicht leer sein
- project darf nicht leer sein
```

Die Dauer eines Zeiteintrags wird automatisch berechnet und in Minuten gespeichert.

---

## Rollen und Berechtigungen

Die Anwendung verwendet folgende Rollen aus Keycloak:

| Rolle | Beschreibung |
|---|---|
| `ROLE_admin` | Darf Benutzer, Projekte, Zeiteintraege und Berichte verwalten |
| `ROLE_read` | Darf Daten lesen |
| `ROLE_update` | Darf Zeiteintraege erstellen, aktualisieren und loeschen |

Die Methoden in den Controllern sind mit `@PreAuthorize` abgesichert.

Beispiel:

```java
@PreAuthorize("hasRole('admin')")
```

Dadurch wird sichergestellt, dass nur berechtigte Benutzer bestimmte REST-Endpunkte aufrufen koennen.

---

## REST-Endpunkte

Die REST-Endpunkte sind in mehrere Controller unterteilt.  
Jeder Controller ist für einen bestimmten fachlichen Bereich verantwortlich.

---

### Users

Die User-Endpunkte dienen zur Verwaltung von Benutzern.

| Methode | Endpoint | Beschreibung | Berechtigung |
|---|---|---|---|
| `GET` | `/api/users` | Alle Benutzer abrufen | `ROLE_admin` oder `ROLE_read` |
| `GET` | `/api/users/{id}` | Benutzer anhand der ID abrufen | `ROLE_admin` oder `ROLE_read` |
| `POST` | `/api/users` | Neuen Benutzer erstellen | `ROLE_admin` |
| `PUT` | `/api/users/{id}` | Benutzer aktualisieren | `ROLE_admin` |
| `DELETE` | `/api/users/{id}` | Benutzer loeschen | `ROLE_admin` |

---

### Projects

Die Project-Endpunkte dienen zur Verwaltung von Projekten.

| Methode | Endpoint | Beschreibung | Berechtigung |
|---|---|---|---|
| `GET` | `/api/projects` | Alle Projekte abrufen | `ROLE_admin` oder `ROLE_read` |
| `GET` | `/api/projects/{id}` | Projekt anhand der ID abrufen | `ROLE_admin` oder `ROLE_read` |
| `POST` | `/api/projects` | Neues Projekt erstellen | `ROLE_admin` |
| `PUT` | `/api/projects/{id}` | Projekt aktualisieren | `ROLE_admin` |
| `DELETE` | `/api/projects/{id}` | Projekt loeschen | `ROLE_admin` |

---

### TimeEntries

Die TimeEntry-Endpunkte dienen zur Verwaltung von Zeiteinträgen.

| Methode | Endpoint | Beschreibung | Berechtigung |
|---|---|---|---|
| `GET` | `/api/time-entries` | Alle Zeiteintraege abrufen | `ROLE_admin` oder `ROLE_read` |
| `GET` | `/api/time-entries/{id}` | Zeiteintrag anhand der ID abrufen | `ROLE_admin` oder `ROLE_read` |
| `POST` | `/api/time-entries` | Neuen Zeiteintrag erstellen | `ROLE_admin` oder `ROLE_update` |
| `PUT` | `/api/time-entries/{id}` | Zeiteintrag aktualisieren | `ROLE_admin` oder `ROLE_update` |
| `DELETE` | `/api/time-entries/{id}` | Zeiteintrag loeschen | `ROLE_admin` oder `ROLE_update` |

---

### Reports

Die Report-Endpunkte dienen zur Auswertung der erfassten Arbeitszeiten.

| Methode | Endpoint | Beschreibung | Berechtigung |
|---|---|---|---|
| `GET` | `/api/reports` | Allgemeine Berichte abrufen | `ROLE_admin` oder `ROLE_read` |
| `GET` | `/api/reports/daily` | Arbeitszeit pro Tag abrufen | `ROLE_admin` oder `ROLE_read` |
| `GET` | `/api/reports/project` | Arbeitszeit pro Projekt abrufen | `ROLE_admin` oder `ROLE_read` |

---

## Datenbankanbindung

Als Datenbank wird **PostgreSQL** verwendet.

Die Verbindung zur Datenbank wird in der Datei `application.yaml` konfiguriert.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/worktime_tracker
    username: postgres
    password: 123456
```

Die Datenbankstruktur wird beim Start der Applikation automatisch durch JPA/Hibernate erstellt.

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
```

---

## Start- und Konfigurationsinformationen

Dieser Abschnitt enthält alle Informationen, welche benötigt werden, um das Projekt lokal zu starten.

---

### Datenbank

| Einstellung | Wert |
|---|---|
| Datenbanksystem | PostgreSQL |
| Datenbankname | `worktime_tracker` |
| Benutzer | `postgres` |
| Passwort | `123456` |
| Port | `5432` |

Vor dem Start der Applikation muss die Datenbank vorhanden sein.

```sql
CREATE DATABASE worktime_tracker;
```

---

### Backend

| Einstellung | Wert |
|---|---|
| Framework | Spring Boot |
| Port | `9090` |
| Startklasse | `WorktimeTrackerApplication` |
| Swagger UI | `http://localhost:9090/swagger-ui/index.html` |

Der Backend-Port wurde angepasst und verwendet nicht den Standardport `8080`.

---

### Keycloak

| Einstellung | Wert |
|---|---|
| Keycloak-Port | `8080` |
| Realmname | `WorkTimeApp` |
| Client | `WorkTime` |
| Authentifizierung | Bearer Token |
| Realm Export | `keycloak/realm-export.json` |

Verwendete Rollen:

```text
ROLE_admin
ROLE_read
ROLE_update
```

Der Keycloak-Realm kann über die Datei `realm-export.json` importiert werden.

---

### Verwendete Netzwerk-Ports

| Dienst | Port | Beschreibung |
|---|---|---|
| Spring Boot Backend | `9090` | REST-API und Swagger |
| Keycloak | `8080` | Authentifizierung und Token-Ausstellung |
| PostgreSQL | `5432` | Datenbankverbindung |

---

## Projekt starten

### 1. PostgreSQL starten

PostgreSQL muss lokal installiert und gestartet sein.

Danach muss die Datenbank erstellt werden:

```sql
CREATE DATABASE worktime_tracker;
```

---

### 2. Keycloak starten

Keycloak muss lokal auf Port `8080` laufen.

Danach muss der Realm importiert werden:

```text
keycloak/realm-export.json
```

Der Realm heisst:

```text
WorkTimeApp
```

---

### 3. Backend starten

Das Backend kann über Maven gestartet werden:

```bash
./mvnw spring-boot:run
```

Unter Windows:

```bash
mvnw.cmd spring-boot:run
```

Alternativ kann das Projekt auch direkt über die IDE gestartet werden.

---

### 4. Swagger UI öffnen

Nach dem Start ist Swagger unter folgender URL erreichbar:

```text
http://localhost:9090/swagger-ui/index.html
```

In Swagger muss ein gültiger Bearer Token eingetragen werden.

Format:

```text
Bearer eyJ...
```

---

## Authentifizierung mit Bearer Token

Die REST-Endpunkte sind über Keycloak abgesichert.

Um einen geschützten Endpoint aufzurufen, wird ein gültiger Bearer Token benötigt.

Der Token wird im HTTP-Header mitgegeben:

```http
Authorization: Bearer <TOKEN>
```

Ohne gültigen Token oder ohne passende Rolle wird der Zugriff verweigert.

---

## Swagger / OpenAPI

Swagger wurde konfiguriert, damit die REST-API direkt im Browser getestet werden kann.

Die OpenAPI-Konfiguration unterstützt:

```text
- Bearer Token Authentifizierung
- Beschreibung der Controller
- Beschreibung der Endpunkte
- Beschreibung der Parameter
- Beschreibung möglicher HTTP-Responses
```

Verwendete Annotationen:

```text
@Tag
@Operation
@ApiResponse
@Parameter
```

Swagger UI:

```text
http://localhost:9090/swagger-ui/index.html
```

---

## Testing

Für das Projekt wurden automatisierte Tests erstellt.

Getestet werden:

```text
- REST-Controller
- JPA-Repositories
- CRUD-Operationen
```

---

### Controller-Test

Es wurde ein JUnit-Test für einen REST-Controller erstellt.

Dabei werden CRUD-Methoden geprüft:

```text
- Create
- Read
- Update
- Delete
```

---

### Repository-Test

Es wurde ein JUnit-Test für ein JpaRepository erstellt.

Auch hier werden CRUD-Operationen getestet:

```text
- Save
- Find
- Update
- Delete
```

---

### Tests ausführen

Die Tests können mit Maven ausgeführt werden:

```bash
./mvnw test
```

Unter Windows:

```bash
mvnw.cmd test
```

---

## Postman

Für das Projekt kann Postman verwendet werden, um die REST-Endpunkte zu testen.

Dabei muss bei geschützten Endpunkten ein Bearer Token im Header mitgegeben werden:

```http
Authorization: Bearer <TOKEN>
```

---

## Beispiel JSON-Daten

### AppUser erstellen

```json
{
  "username": "max.mustermann",
  "email": "max.mustermann@example.com"
}
```

---

### Project erstellen

```json
{
  "name": "Projekt A",
  "description": "Beschreibung des Projekts",
  "active": true
}
```

---

### TimeEntry erstellen

```json
{
  "startTime": "2026-04-24T08:00:00",
  "endTime": "2026-04-24T12:00:00",
  "user": {
    "id": 1
  },
  "project": {
    "id": 1
  }
}
```

---

## Wichtige Hinweise für den Kursleiter

Zum Starten des Projekts werden folgende Komponenten benötigt:

```text
- Java 21
- Maven
- PostgreSQL
- Keycloak
```

Folgende Schritte sind notwendig:

```text
1. PostgreSQL starten
2. Datenbank worktime_tracker erstellen
3. Keycloak starten
4. Realm WorkTimeApp aus keycloak/realm-export.json importieren
5. Backend auf Port 9090 starten
6. Swagger UI öffnen
7. Bearer Token eintragen
8. REST-Endpunkte testen
```

---

## Git

Das Projekt wurde mit Git versioniert.

Das `.git`-Verzeichnis ist in der Projektabgabe enthalten, damit die Versionshistorie nachvollzogen werden kann.

---

## Autor

```text
Name: Jason Zeitz
Modul: 295 - Backend für Applikationen realisieren
Projekt: WorkTime Tracker
Kursnummer: 24-295-E
```