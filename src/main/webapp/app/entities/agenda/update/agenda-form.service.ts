import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAgenda, NewAgenda } from '../agenda.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgenda for edit and NewAgendaFormGroupInput for create.
 */
type AgendaFormGroupInput = IAgenda | PartialWithRequiredKeyOf<NewAgenda>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAgenda | NewAgenda> = Omit<T, 'dataHoraInicio' | 'dataHoraFim'> & {
  dataHoraInicio?: string | null;
  dataHoraFim?: string | null;
};

type AgendaFormRawValue = FormValueOf<IAgenda>;

type NewAgendaFormRawValue = FormValueOf<NewAgenda>;

type AgendaFormDefaults = Pick<NewAgenda, 'id' | 'dataHoraInicio' | 'dataHoraFim'>;

type AgendaFormGroupContent = {
  id: FormControl<AgendaFormRawValue['id'] | NewAgenda['id']>;
  dataHoraInicio: FormControl<AgendaFormRawValue['dataHoraInicio']>;
  dataHoraFim: FormControl<AgendaFormRawValue['dataHoraFim']>;
  status: FormControl<AgendaFormRawValue['status']>;
  observacoes: FormControl<AgendaFormRawValue['observacoes']>;
  valorCobrado: FormControl<AgendaFormRawValue['valorCobrado']>;
  paciente: FormControl<AgendaFormRawValue['paciente']>;
  profissional: FormControl<AgendaFormRawValue['profissional']>;
  sala: FormControl<AgendaFormRawValue['sala']>;
  especialidade: FormControl<AgendaFormRawValue['especialidade']>;
};

export type AgendaFormGroup = FormGroup<AgendaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendaFormService {
  createAgendaFormGroup(agenda: AgendaFormGroupInput = { id: null }): AgendaFormGroup {
    const agendaRawValue = this.convertAgendaToAgendaRawValue({
      ...this.getFormDefaults(),
      ...agenda,
    });
    return new FormGroup<AgendaFormGroupContent>({
      id: new FormControl(
        { value: agendaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataHoraInicio: new FormControl(agendaRawValue.dataHoraInicio, {
        validators: [Validators.required],
      }),
      dataHoraFim: new FormControl(agendaRawValue.dataHoraFim, {
        validators: [Validators.required],
      }),
      status: new FormControl(agendaRawValue.status, {
        validators: [Validators.required],
      }),
      observacoes: new FormControl(agendaRawValue.observacoes),
      valorCobrado: new FormControl(agendaRawValue.valorCobrado),
      paciente: new FormControl(agendaRawValue.paciente, {
        validators: [Validators.required],
      }),
      profissional: new FormControl(agendaRawValue.profissional, {
        validators: [Validators.required],
      }),
      sala: new FormControl(agendaRawValue.sala),
      especialidade: new FormControl(agendaRawValue.especialidade, {
        validators: [Validators.required],
      }),
    });
  }

  getAgenda(form: AgendaFormGroup): IAgenda | NewAgenda {
    return this.convertAgendaRawValueToAgenda(form.getRawValue() as AgendaFormRawValue | NewAgendaFormRawValue);
  }

  resetForm(form: AgendaFormGroup, agenda: AgendaFormGroupInput): void {
    const agendaRawValue = this.convertAgendaToAgendaRawValue({ ...this.getFormDefaults(), ...agenda });
    form.reset(
      {
        ...agendaRawValue,
        id: { value: agendaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraInicio: currentTime,
      dataHoraFim: currentTime,
    };
  }

  private convertAgendaRawValueToAgenda(rawAgenda: AgendaFormRawValue | NewAgendaFormRawValue): IAgenda | NewAgenda {
    return {
      ...rawAgenda,
      dataHoraInicio: dayjs(rawAgenda.dataHoraInicio, DATE_TIME_FORMAT),
      dataHoraFim: dayjs(rawAgenda.dataHoraFim, DATE_TIME_FORMAT),
    };
  }

  private convertAgendaToAgendaRawValue(
    agenda: IAgenda | (Partial<NewAgenda> & AgendaFormDefaults),
  ): AgendaFormRawValue | PartialWithRequiredKeyOf<NewAgendaFormRawValue> {
    return {
      ...agenda,
      dataHoraInicio: agenda.dataHoraInicio ? agenda.dataHoraInicio.format(DATE_TIME_FORMAT) : undefined,
      dataHoraFim: agenda.dataHoraFim ? agenda.dataHoraFim.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
