import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeEntryListComponent } from './time-entry-list.component';

describe('TimeEntryListComponent', () => {
  let component: TimeEntryListComponent;
  let fixture: ComponentFixture<TimeEntryListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TimeEntryListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimeEntryListComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
