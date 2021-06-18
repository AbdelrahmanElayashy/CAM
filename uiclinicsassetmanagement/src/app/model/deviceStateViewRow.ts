export class DeviceStateViewRow {
  deviceId: string;
  name: string;
  defectPriority: string;
  state: string;
  enteredAt: string;
  availableAt: string;

  showChangeStateButton: boolean;
  addInitialStateButton: boolean;
  removeStateButton: boolean;
  showChangeAvailabilityDate: boolean;
}
