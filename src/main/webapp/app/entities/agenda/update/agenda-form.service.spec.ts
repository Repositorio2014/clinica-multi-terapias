import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../agenda.test-samples';

import { AgendaFormService } from './agenda-form.service';

describe('Agenda Form Service', () => {
  let service: AgendaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AgendaFormService);
  });

  describe('Service methods', () => {
    describe('createAgendaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAgendaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataHoraInicio: expect.any(Object),
            dataHoraFim: expect.any(Object),
            status: expect.any(Object),
            observacoes: expect.any(Object),
            valorCobrado: expect.any(Object),
            paciente: expect.any(Object),
            profissional: expect.any(Object),
            sala: expect.any(Object),
            especialidade: expect.any(Object),
          }),
        );
      });

      it('passing IAgenda should create a new form with FormGroup', () => {
        const formGroup = service.createAgendaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataHoraInicio: expect.any(Object),
            dataHoraFim: expect.any(Object),
            status: expect.any(Object),
            observacoes: expect.any(Object),
            valorCobrado: expect.any(Object),
            paciente: expect.any(Object),
            profissional: expect.any(Object),
            sala: expect.any(Object),
            especialidade: expect.any(Object),
          }),
        );
      });
    });

    describe('getAgenda', () => {
      it('should return NewAgenda for default Agenda initial value', () => {
        const formGroup = service.createAgendaFormGroup(sampleWithNewData);

        const agenda = service.getAgenda(formGroup) as any;

        expect(agenda).toMatchObject(sampleWithNewData);
      });

      it('should return NewAgenda for empty Agenda initial value', () => {
        const formGroup = service.createAgendaFormGroup();

        const agenda = service.getAgenda(formGroup) as any;

        expect(agenda).toMatchObject({});
      });

      it('should return IAgenda', () => {
        const formGroup = service.createAgendaFormGroup(sampleWithRequiredData);

        const agenda = service.getAgenda(formGroup) as any;

        expect(agenda).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAgenda should not enable id FormControl', () => {
        const formGroup = service.createAgendaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAgenda should disable id FormControl', () => {
        const formGroup = service.createAgendaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
