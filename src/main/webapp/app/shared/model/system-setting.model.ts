export const enum SystemSettingKey {
  MIN_NUMBER_OF_RATINGS = 'MIN_NUMBER_OF_RATINGS'
}

export interface ISystemSetting {
  id?: number;
  key?: SystemSettingKey;
  value?: string;
  description?: string;
}

export class SystemSetting implements ISystemSetting {
  constructor(public id?: number, public key?: SystemSettingKey, public value?: string, public description?: string) {}
}
