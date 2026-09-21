import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ISala, NewSala } from '../sala.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISala for edit and NewSalaFormGroupInput for create.
 */
type SalaFormGroupInput = ISala | PartialWithRequiredKeyOf<NewSala>;

type SalaFormDefaults = Pick<NewSala, 'id'>;

type SalaFormGroupContent = {
  id: FormControl<ISala['id'] | NewSala['id']>;
  nome: FormControl<ISala['nome']>;
  descricao: FormControl<ISala['descricao']>;
  capacidade: FormControl<ISala['capacidade']>;
};

export type SalaFormGroup = FormGroup<SalaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SalaFormService {
  createSalaFormGroup(sala: SalaFormGroupInput = { id: null }): SalaFormGroup {
    const salaRawValue = {
      ...this.getFormDefaults(),
      ...sala,
    };
    return new FormGroup<SalaFormGroupContent>({
      id: new FormControl(
        { value: salaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(salaRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(salaRawValue.descricao),
      capacidade: new FormControl(salaRawValue.capacidade),
    });
  }

  getSala(form: SalaFormGroup): ISala | NewSala {
    return form.getRawValue() as ISala | NewSala;
  }

  resetForm(form: SalaFormGroup, sala: SalaFormGroupInput): void {
    const salaRawValue = { ...this.getFormDefaults(), ...sala };
    form.reset(
      {
        ...salaRawValue,
        id: { value: salaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SalaFormDefaults {
    return {
      id: null,
    };
  }
}
