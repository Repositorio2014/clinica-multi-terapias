import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IProfissional, NewProfissional } from '../profissional.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfissional for edit and NewProfissionalFormGroupInput for create.
 */
type ProfissionalFormGroupInput = IProfissional | PartialWithRequiredKeyOf<NewProfissional>;

type ProfissionalFormDefaults = Pick<NewProfissional, 'id' | 'ativo' | 'especialidades'>;

type ProfissionalFormGroupContent = {
  id: FormControl<IProfissional['id'] | NewProfissional['id']>;
  nome: FormControl<IProfissional['nome']>;
  cpf: FormControl<IProfissional['cpf']>;
  registroConselho: FormControl<IProfissional['registroConselho']>;
  telefone: FormControl<IProfissional['telefone']>;
  email: FormControl<IProfissional['email']>;
  ativo: FormControl<IProfissional['ativo']>;
  valorSessao: FormControl<IProfissional['valorSessao']>;
  especialidades: FormControl<IProfissional['especialidades']>;
};

export type ProfissionalFormGroup = FormGroup<ProfissionalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfissionalFormService {
  createProfissionalFormGroup(profissional: ProfissionalFormGroupInput = { id: null }): ProfissionalFormGroup {
    const profissionalRawValue = {
      ...this.getFormDefaults(),
      ...profissional,
    };
    return new FormGroup<ProfissionalFormGroupContent>({
      id: new FormControl(
        { value: profissionalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(profissionalRawValue.nome, {
        validators: [Validators.required],
      }),
      cpf: new FormControl(profissionalRawValue.cpf, {
        validators: [Validators.required],
      }),
      registroConselho: new FormControl(profissionalRawValue.registroConselho, {
        validators: [Validators.required],
      }),
      telefone: new FormControl(profissionalRawValue.telefone, {
        validators: [Validators.required],
      }),
      email: new FormControl(profissionalRawValue.email, {
        validators: [Validators.required],
      }),
      ativo: new FormControl(profissionalRawValue.ativo, {
        validators: [Validators.required],
      }),
      valorSessao: new FormControl(profissionalRawValue.valorSessao),
      especialidades: new FormControl(profissionalRawValue.especialidades ?? []),
    });
  }

  getProfissional(form: ProfissionalFormGroup): IProfissional | NewProfissional {
    return form.getRawValue() as IProfissional | NewProfissional;
  }

  resetForm(form: ProfissionalFormGroup, profissional: ProfissionalFormGroupInput): void {
    const profissionalRawValue = { ...this.getFormDefaults(), ...profissional };
    form.reset(
      {
        ...profissionalRawValue,
        id: { value: profissionalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProfissionalFormDefaults {
    return {
      id: null,
      ativo: false,
      especialidades: [],
    };
  }
}
