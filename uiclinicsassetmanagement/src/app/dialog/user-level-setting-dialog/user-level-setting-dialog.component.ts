import { Component, OnInit } from '@angular/core';
import {StaffService} from '../../services/staff.service';
import {MatDialogRef} from '@angular/material/dialog';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {MatStepper} from '@angular/material/stepper';
import {CdkDragDrop, moveItemInArray} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-user-level-setting-dialog',
  templateUrl: './user-level-setting-dialog.component.html',
  styleUrls: ['./user-level-setting-dialog.component.scss']
})
export class UserLevelSettingDialogComponent implements OnInit {

  types: any[];
  selectedType: string;

  isLinear = true;

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  typeControl = new FormControl('', Validators.required);

  ranks: any[];

  drop(event: CdkDragDrop<string[]>) {
    const param = new Map<string, string>();
    param.set('typeName', this.selectedType);
    param.set('rankName1', this.ranks[event.previousIndex].rank);
    param.set('rankName2', this.ranks[event.currentIndex].rank);

    this.staffService.postData('swapImportance', null, param, true, null )
      .subscribe(data => {
        console.log(data);
      });

    // Anzeige auch drehen:
    moveItemInArray(this.ranks, event.previousIndex, event.currentIndex);
  }


  constructor(
    public dialogRef: MatDialogRef<UserLevelSettingDialogComponent>,
    private _formBuilder: FormBuilder,
    private staffService: StaffService
  ) {

    this.staffService.getData('getTypes', null, null, true)
      .subscribe(typeList => {
        this.types = typeList;
      });

  }

  ngOnInit(): void {
    this.firstFormGroup = this._formBuilder.group({
      typeControl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
  }

  changedType(selectedType: string) {
    console.log(selectedType);
    this.selectedType = selectedType;
  }

  nextStepRanks() {
    const param = new Map<string, string>();
    param.set('type', this.selectedType);

    //load Ranks for Type:
    this.staffService.getData('getRanksOfType', null, param, true )
      .subscribe(rankData => {
        console.log(rankData);
        this.ranks = rankData;
      });
  }

  goForward(stepper: MatStepper){
    console.log('xxx');
    stepper.next();
  }

  saveRanks(){
    console.log(this.ranks);
    // ranks bereits aktuell

  }

  addNewRank() {

  }


  actionFunction() {
    console.log('TODO');

    this.dialogRef.close();
  }

    closeModal() {
    this.dialogRef.close();
  }
}
