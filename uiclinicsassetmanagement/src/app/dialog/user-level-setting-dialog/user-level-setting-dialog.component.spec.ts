import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserLevelSettingDialogComponent } from './user-level-setting-dialog.component';

describe('UserLevelSettingDialogComponent', () => {
  let component: UserLevelSettingDialogComponent;
  let fixture: ComponentFixture<UserLevelSettingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UserLevelSettingDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserLevelSettingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
