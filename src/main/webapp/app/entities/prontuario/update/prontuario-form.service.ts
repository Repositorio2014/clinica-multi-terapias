import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProntuario, NewProntuario } from '../prontuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProntuario for edit and NewProntuarioFormGroupInput for create.
 */
type ProntuarioFormGroupInput = IProntuario | PartialWithRequiredKeyOf<NewProntuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProntuario | NewProntuario> = Omit<T, 'dataAtendimento'> & {
  dataAtendimento?: string | null;
};

type ProntuarioFormRawValue = FormValueOf<IProntuario>;

type NewProntuarioFormRawValue = FormValueOf<NewProntuario>;

type ProntuarioFormDefaults = Pick<NewProntuario, 'id' | 'dataAtendimento' | 'confidencial'>;

type ProntuarioFormGroupContent = {
  id: FormControl<ProntuarioFormRawValue['id'] | NewProntuario['id']>;
  tipo: FormControl<ProntuarioFormRawValue['tipo']>;
  dataAtendimento: FormControl<ProntuarioFormRawValue['dataAtendimento']>;
  titulo: FormControl<ProntuarioFormRawValue['titulo']>;
  conteudo: FormControl<ProntuarioFormRawValue['conteudo']>;
  confidencial: FormControl<ProntuarioFormRawValue['confidencial']>;
  paciente: FormControl<ProntuarioFormRawValue['paciente']>;
  profissional: FormControl<ProntuarioFormRawValue['profissional']>;
  agenda: FormControl<ProntuarioFormRawValue['agenda']>;
  especialidade: FormControl<ProntuarioFormRawValue['especialidade']>;
};

export type ProntuarioFormGroup = FormGroup<ProntuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProntuarioFormService {
  createProntuarioFormGroup(prontuario: ProntuarioFormGroupInput = { id: null }): ProntuarioFormGroup {
    const prontuarioRawValue = this.convertProntuarioToProntuarioRawValue({
      ...this.getFormDefaults(),
      ...prontuario,
    });
    return new FormGroup<ProntuarioFormGroupContent>({
      id: new FormControl(
        { value: prontuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(prontuarioRawValue.tipo, {
        validators: [Validators.required],
      }),
      dataAtendimento: new FormControl(prontuarioRawValue.dataAtendimento, {
        validators: [Validators.required],
      }),
      titulo: new FormControl(prontuarioRawValue.titulo, {
        validators: [Validators.required],
      }),
      conteudo: new FormControl(prontuarioRawValue.conteudo, {
        validators: [Validators.required],
      }),
      confidencial: new FormControl(prontuarioRawValue.confidencial, {
        validators: [Validators.required],
      }),
      paciente: new FormControl(prontuarioRawValue.paciente, {
        validators: [Validators.required],
      }),
      profissional: new FormControl(prontuarioRawValue.profissional, {
        validators: [Validators.required],
      }),
      agenda: new FormControl(prontuarioRawValue.agenda),
      especialidade: new FormControl(prontuarioRawValue.especialidade, {
        validators: [Validators.required],
      }),
    });
  }

  getProntuario(form: ProntuarioFormGroup): IProntuario | NewProntuario {
    return this.convertProntuarioRawValueToProntuario(form.getRawValue() as ProntuarioFormRawValue | NewProntuarioFormRawValue);
  }

  resetForm(form: ProntuarioFormGroup, prontuario: ProntuarioFormGroupInput): void {
    const prontuarioRawValue = this.convertProntuarioToProntuarioRawValue({ ...this.getFormDefaults(), ...prontuario });
    form.reset(
      {
        ...prontuarioRawValue,
        id: { value: prontuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProntuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAtendimento: currentTime,
      confidencial: false,
    };
  }

  private convertProntuarioRawValueToProntuario(
    rawProntuario: ProntuarioFormRawValue | NewProntuarioFormRawValue,
  ): IProntuario | NewProntuario {
    return {
      ...rawProntuario,
      dataAtendimento: dayjs(rawProntuario.dataAtendimento, DATE_TIME_FORMAT),
    };
  }

  private convertProntuarioToProntuarioRawValue(
    prontuario: IProntuario | (Partial<NewProntuario> & ProntuarioFormDefaults),
  ): ProntuarioFormRawValue | PartialWithRequiredKeyOf<NewProntuarioFormRawValue> {
    return {
      ...prontuario,
      dataAtendimento: prontuario.dataAtendimento ? prontuario.dataAtendimento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
