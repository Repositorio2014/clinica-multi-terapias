import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPaciente, NewPaciente } from '../paciente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaciente for edit and NewPacienteFormGroupInput for create.
 */
type PacienteFormGroupInput = IPaciente | PartialWithRequiredKeyOf<NewPaciente>;

type PacienteFormDefaults = Pick<NewPaciente, 'id' | 'ativo'>;

type PacienteFormGroupContent = {
  id: FormControl<IPaciente['id'] | NewPaciente['id']>;
  nome: FormControl<IPaciente['nome']>;
  cpf: FormControl<IPaciente['cpf']>;
  dataNascimento: FormControl<IPaciente['dataNascimento']>;
  telefone: FormControl<IPaciente['telefone']>;
  email: FormControl<IPaciente['email']>;
  nomeResponsavel: FormControl<IPaciente['nomeResponsavel']>;
  telefoneResponsavel: FormControl<IPaciente['telefoneResponsavel']>;
  endereco: FormControl<IPaciente['endereco']>;
  convenio: FormControl<IPaciente['convenio']>;
  observacoes: FormControl<IPaciente['observacoes']>;
  ativo: FormControl<IPaciente['ativo']>;
};

export type PacienteFormGroup = FormGroup<PacienteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PacienteFormService {
  createPacienteFormGroup(paciente: PacienteFormGroupInput = { id: null }): PacienteFormGroup {
    const pacienteRawValue = {
      ...this.getFormDefaults(),
      ...paciente,
    };
    return new FormGroup<PacienteFormGroupContent>({
      id: new FormControl(
        { value: pacienteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(pacienteRawValue.nome, {
        validators: [Validators.required],
      }),
      cpf: new FormControl(pacienteRawValue.cpf, {
        validators: [Validators.required],
      }),
      dataNascimento: new FormControl(pacienteRawValue.dataNascimento, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(pacienteRawValue.telefone, {
        validators: [Validators.required],
      }),
      email: new FormControl(pacienteRawValue.email),
      nomeResponsavel: new FormControl(pacienteRawValue.nomeResponsavel),
      telefoneResponsavel: new FormControl(pacienteRawValue.telefoneResponsavel),
      endereco: new FormControl(pacienteRawValue.endereco),
      convenio: new FormControl(pacienteRawValue.convenio),
      observacoes: new FormControl(pacienteRawValue.observacoes),
      ativo: new FormControl(pacienteRawValue.ativo, {
        validators: [Validators.required],
      }),
    });
  }

  getPaciente(form: PacienteFormGroup): IPaciente | NewPaciente {
    return form.getRawValue() as IPaciente | NewPaciente;
  }

  resetForm(form: PacienteFormGroup, paciente: PacienteFormGroupInput): void {
    const pacienteRawValue = { ...this.getFormDefaults(), ...paciente };
    form.reset(
      {
        ...pacienteRawValue,
        id: { value: pacienteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PacienteFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
