import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IEspecialidade } from 'app/entities/especialidade/especialidade.model';
import { EspecialidadeService } from 'app/entities/especialidade/service/especialidade.service';
import { ProfissionalService } from '../service/profissional.service';
import { IProfissional } from '../profissional.model';
import { ProfissionalFormService } from './profissional-form.service';

import { ProfissionalUpdateComponent } from './profissional-update.component';

describe('Profissional Management Update Component', () => {
  let comp: ProfissionalUpdateComponent;
  let fixture: ComponentFixture<ProfissionalUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profissionalFormService: ProfissionalFormService;
  let profissionalService: ProfissionalService;
  let especialidadeService: EspecialidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProfissionalUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ProfissionalUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfissionalUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profissionalFormService = TestBed.inject(ProfissionalFormService);
    profissionalService = TestBed.inject(ProfissionalService);
    especialidadeService = TestBed.inject(EspecialidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Especialidade query and add missing value', () => {
      const profissional: IProfissional = { id: 456 };
      const especialidades: IEspecialidade[] = [{ id: 29260 }];
      profissional.especialidades = especialidades;

      const especialidadeCollection: IEspecialidade[] = [{ id: 27093 }];
      jest.spyOn(especialidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: especialidadeCollection })));
      const additionalEspecialidades = [...especialidades];
      const expectedCollection: IEspecialidade[] = [...additionalEspecialidades, ...especialidadeCollection];
      jest.spyOn(especialidadeService, 'addEspecialidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profissional });
      comp.ngOnInit();

      expect(especialidadeService.query).toHaveBeenCalled();
      expect(especialidadeService.addEspecialidadeToCollectionIfMissing).toHaveBeenCalledWith(
        especialidadeCollection,
        ...additionalEspecialidades.map(expect.objectContaining),
      );
      expect(comp.especialidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const profissional: IProfissional = { id: 456 };
      const especialidades: IEspecialidade = { id: 8754 };
      profissional.especialidades = [especialidades];

      activatedRoute.data = of({ profissional });
      comp.ngOnInit();

      expect(comp.especialidadesSharedCollection).toContain(especialidades);
      expect(comp.profissional).toEqual(profissional);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissional>>();
      const profissional = { id: 123 };
      jest.spyOn(profissionalFormService, 'getProfissional').mockReturnValue(profissional);
      jest.spyOn(profissionalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissional });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissional }));
      saveSubject.complete();

      // THEN
      expect(profissionalFormService.getProfissional).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profissionalService.update).toHaveBeenCalledWith(expect.objectContaining(profissional));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissional>>();
      const profissional = { id: 123 };
      jest.spyOn(profissionalFormService, 'getProfissional').mockReturnValue({ id: null });
      jest.spyOn(profissionalService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissional: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissional }));
      saveSubject.complete();

      // THEN
      expect(profissionalFormService.getProfissional).toHaveBeenCalled();
      expect(profissionalService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissional>>();
      const profissional = { id: 123 };
      jest.spyOn(profissionalService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissional });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profissionalService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEspecialidade', () => {
      it('Should forward to especialidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(especialidadeService, 'compareEspecialidade');
        comp.compareEspecialidade(entity, entity2);
        expect(especialidadeService.compareEspecialidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
