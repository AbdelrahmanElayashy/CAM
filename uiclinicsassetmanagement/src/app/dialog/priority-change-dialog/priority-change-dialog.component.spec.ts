import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PriorityChangeDialogComponent } from './priority-change-dialog.component';

describe('PriorityChangeDialogComponent', () => {
  let component: PriorityChangeDialogComponent;
  let fixture: ComponentFixture<PriorityChangeDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PriorityChangeDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PriorityChangeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
