import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IEspecialidade, NewEspecialidade } from '../especialidade.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEspecialidade for edit and NewEspecialidadeFormGroupInput for create.
 */
type EspecialidadeFormGroupInput = IEspecialidade | PartialWithRequiredKeyOf<NewEspecialidade>;

type EspecialidadeFormDefaults = Pick<NewEspecialidade, 'id' | 'profissionais'>;

type EspecialidadeFormGroupContent = {
  id: FormControl<IEspecialidade['id'] | NewEspecialidade['id']>;
  nome: FormControl<IEspecialidade['nome']>;
  descricao: FormControl<IEspecialidade['descricao']>;
  profissionais: FormControl<IEspecialidade['profissionais']>;
};

export type EspecialidadeFormGroup = FormGroup<EspecialidadeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EspecialidadeFormService {
  createEspecialidadeFormGroup(especialidade: EspecialidadeFormGroupInput = { id: null }): EspecialidadeFormGroup {
    const especialidadeRawValue = {
      ...this.getFormDefaults(),
      ...especialidade,
    };
    return new FormGroup<EspecialidadeFormGroupContent>({
      id: new FormControl(
        { value: especialidadeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(especialidadeRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(especialidadeRawValue.descricao),
      profissionais: new FormControl(especialidadeRawValue.profissionais ?? []),
    });
  }

  getEspecialidade(form: EspecialidadeFormGroup): IEspecialidade | NewEspecialidade {
    return form.getRawValue() as IEspecialidade | NewEspecialidade;
  }

  resetForm(form: EspecialidadeFormGroup, especialidade: EspecialidadeFormGroupInput): void {
    const especialidadeRawValue = { ...this.getFormDefaults(), ...especialidade };
    form.reset(
      {
        ...especialidadeRawValue,
        id: { value: especialidadeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EspecialidadeFormDefaults {
    return {
      id: null,
      profissionais: [],
    };
  }
}
