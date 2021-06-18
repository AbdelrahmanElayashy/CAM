import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAdditionalInformationDialogComponent } from './add-additional-information-dialog.component';

describe('AddAdditionalInformationDialogComponent', () => {
  let component: AddAdditionalInformationDialogComponent;
  let fixture: ComponentFixture<AddAdditionalInformationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddAdditionalInformationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAdditionalInformationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
