import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../profissional.test-samples';

import { ProfissionalFormService } from './profissional-form.service';

describe('Profissional Form Service', () => {
  let service: ProfissionalFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProfissionalFormService);
  });

  describe('Service methods', () => {
    describe('createProfissionalFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProfissionalFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            cpf: expect.any(Object),
            registroConselho: expect.any(Object),
            telefone: expect.any(Object),
            email: expect.any(Object),
            ativo: expect.any(Object),
            valorSessao: expect.any(Object),
            especialidades: expect.any(Object),
          }),
        );
      });

      it('passing IProfissional should create a new form with FormGroup', () => {
        const formGroup = service.createProfissionalFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            cpf: expect.any(Object),
            registroConselho: expect.any(Object),
            telefone: expect.any(Object),
            email: expect.any(Object),
            ativo: expect.any(Object),
            valorSessao: expect.any(Object),
            especialidades: expect.any(Object),
          }),
        );
      });
    });

    describe('getProfissional', () => {
      it('should return NewProfissional for default Profissional initial value', () => {
        const formGroup = service.createProfissionalFormGroup(sampleWithNewData);

        const profissional = service.getProfissional(formGroup) as any;

        expect(profissional).toMatchObject(sampleWithNewData);
      });

      it('should return NewProfissional for empty Profissional initial value', () => {
        const formGroup = service.createProfissionalFormGroup();

        const profissional = service.getProfissional(formGroup) as any;

        expect(profissional).toMatchObject({});
      });

      it('should return IProfissional', () => {
        const formGroup = service.createProfissionalFormGroup(sampleWithRequiredData);

        const profissional = service.getProfissional(formGroup) as any;

        expect(profissional).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProfissional should not enable id FormControl', () => {
        const formGroup = service.createProfissionalFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProfissional should disable id FormControl', () => {
        const formGroup = service.createProfissionalFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
