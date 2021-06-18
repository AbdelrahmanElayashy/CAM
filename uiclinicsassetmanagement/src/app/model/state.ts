export interface State {
  id: string;
  name: string;
  enteredBy: string;
  enteredAt: moment.Moment;
  availabilityDate: moment.Moment;
  // additionalInformation;
}


export class ChangedState {
  user_id?: string;
  state!: string;
}

export class ChangedPriority {
  user_id?: string;
  device_id?: string;
  priority!: string;
}
