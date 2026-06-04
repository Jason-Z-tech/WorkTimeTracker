import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TimeEntryDetailComponent } from './time-entry-detail.component';

describe('TimeEntryDetailComponent', () => {
  let component: TimeEntryDetailComponent;
  let fixture: ComponentFixture<TimeEntryDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TimeEntryDetailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TimeEntryDetailComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
