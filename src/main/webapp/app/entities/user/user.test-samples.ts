import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 7166,
  login: 'm57y',
};

export const sampleWithPartialData: IUser = {
  id: 6326,
  login: 'MiUv',
};

export const sampleWithFullData: IUser = {
  id: 3011,
  login: 'z_g',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
