import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { IProfissional } from 'app/entities/profissional/profissional.model';
import { ProfissionalService } from 'app/entities/profissional/service/profissional.service';
import { EspecialidadeService } from '../service/especialidade.service';
import { IEspecialidade } from '../especialidade.model';
import { EspecialidadeFormService } from './especialidade-form.service';

import { EspecialidadeUpdateComponent } from './especialidade-update.component';

describe('Especialidade Management Update Component', () => {
  let comp: EspecialidadeUpdateComponent;
  let fixture: ComponentFixture<EspecialidadeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let especialidadeFormService: EspecialidadeFormService;
  let especialidadeService: EspecialidadeService;
  let profissionalService: ProfissionalService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EspecialidadeUpdateComponent],
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
      .overrideTemplate(EspecialidadeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EspecialidadeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    especialidadeFormService = TestBed.inject(EspecialidadeFormService);
    especialidadeService = TestBed.inject(EspecialidadeService);
    profissionalService = TestBed.inject(ProfissionalService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Profissional query and add missing value', () => {
      const especialidade: IEspecialidade = { id: 456 };
      const profissionais: IProfissional[] = [{ id: 13960 }];
      especialidade.profissionais = profissionais;

      const profissionalCollection: IProfissional[] = [{ id: 32127 }];
      jest.spyOn(profissionalService, 'query').mockReturnValue(of(new HttpResponse({ body: profissionalCollection })));
      const additionalProfissionals = [...profissionais];
      const expectedCollection: IProfissional[] = [...additionalProfissionals, ...profissionalCollection];
      jest.spyOn(profissionalService, 'addProfissionalToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ especialidade });
      comp.ngOnInit();

      expect(profissionalService.query).toHaveBeenCalled();
      expect(profissionalService.addProfissionalToCollectionIfMissing).toHaveBeenCalledWith(
        profissionalCollection,
        ...additionalProfissionals.map(expect.objectContaining),
      );
      expect(comp.profissionalsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const especialidade: IEspecialidade = { id: 456 };
      const profissionais: IProfissional = { id: 26976 };
      especialidade.profissionais = [profissionais];

      activatedRoute.data = of({ especialidade });
      comp.ngOnInit();

      expect(comp.profissionalsSharedCollection).toContain(profissionais);
      expect(comp.especialidade).toEqual(especialidade);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidade>>();
      const especialidade = { id: 123 };
      jest.spyOn(especialidadeFormService, 'getEspecialidade').mockReturnValue(especialidade);
      jest.spyOn(especialidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialidade }));
      saveSubject.complete();

      // THEN
      expect(especialidadeFormService.getEspecialidade).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(especialidadeService.update).toHaveBeenCalledWith(expect.objectContaining(especialidade));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidade>>();
      const especialidade = { id: 123 };
      jest.spyOn(especialidadeFormService, 'getEspecialidade').mockReturnValue({ id: null });
      jest.spyOn(especialidadeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidade: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: especialidade }));
      saveSubject.complete();

      // THEN
      expect(especialidadeFormService.getEspecialidade).toHaveBeenCalled();
      expect(especialidadeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEspecialidade>>();
      const especialidade = { id: 123 };
      jest.spyOn(especialidadeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ especialidade });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(especialidadeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProfissional', () => {
      it('Should forward to profissionalService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(profissionalService, 'compareProfissional');
        comp.compareProfissional(entity, entity2);
        expect(profissionalService.compareProfissional).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
