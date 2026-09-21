import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../prontuario.test-samples';

import { ProntuarioFormService } from './prontuario-form.service';

describe('Prontuario Form Service', () => {
  let service: ProntuarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProntuarioFormService);
  });

  describe('Service methods', () => {
    describe('createProntuarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProntuarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            dataAtendimento: expect.any(Object),
            titulo: expect.any(Object),
            conteudo: expect.any(Object),
            confidencial: expect.any(Object),
            paciente: expect.any(Object),
            profissional: expect.any(Object),
            agenda: expect.any(Object),
            especialidade: expect.any(Object),
          }),
        );
      });

      it('passing IProntuario should create a new form with FormGroup', () => {
        const formGroup = service.createProntuarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            dataAtendimento: expect.any(Object),
            titulo: expect.any(Object),
            conteudo: expect.any(Object),
            confidencial: expect.any(Object),
            paciente: expect.any(Object),
            profissional: expect.any(Object),
            agenda: expect.any(Object),
            especialidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getProntuario', () => {
      it('should return NewProntuario for default Prontuario initial value', () => {
        const formGroup = service.createProntuarioFormGroup(sampleWithNewData);

        const prontuario = service.getProntuario(formGroup) as any;

        expect(prontuario).toMatchObject(sampleWithNewData);
      });

      it('should return NewProntuario for empty Prontuario initial value', () => {
        const formGroup = service.createProntuarioFormGroup();

        const prontuario = service.getProntuario(formGroup) as any;

        expect(prontuario).toMatchObject({});
      });

      it('should return IProntuario', () => {
        const formGroup = service.createProntuarioFormGroup(sampleWithRequiredData);

        const prontuario = service.getProntuario(formGroup) as any;

        expect(prontuario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProntuario should not enable id FormControl', () => {
        const formGroup = service.createProntuarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProntuario should disable id FormControl', () => {
        const formGroup = service.createProntuarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
