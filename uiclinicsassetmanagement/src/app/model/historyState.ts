import {State} from './state';

export interface HistoryState {
  deviceId: string;
  states: State[];
}
