import { AppUser } from './app-user';
import { Project } from './project';

export interface TimeEntry {
  id: number;
  startTime: string;
  endTime: string;
  durationMinutes: number;
  user: AppUser;
  project: Project;
}