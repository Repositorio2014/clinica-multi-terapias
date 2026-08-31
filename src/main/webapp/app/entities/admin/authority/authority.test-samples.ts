import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '97468894-7db1-416d-b4c4-68c0c3f92a53',
};

export const sampleWithPartialData: IAuthority = {
  name: '63075c07-5eeb-476a-85ae-1e68eb3736e5',
};

export const sampleWithFullData: IAuthority = {
  name: '42139f4d-febd-4dab-808d-6a7f89317c78',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
