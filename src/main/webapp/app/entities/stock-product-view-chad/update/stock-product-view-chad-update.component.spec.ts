import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StockProductViewChadFormService } from './stock-product-view-chad-form.service';
import { StockProductViewChadService } from '../service/stock-product-view-chad.service';
import { IStockProductViewChad } from '../stock-product-view-chad.model';

import { StockProductViewChadUpdateComponent } from './stock-product-view-chad-update.component';

describe('StockProductViewChad Management Update Component', () => {
  let comp: StockProductViewChadUpdateComponent;
  let fixture: ComponentFixture<StockProductViewChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stockProductViewFormService: StockProductViewChadFormService;
  let stockProductViewService: StockProductViewChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StockProductViewChadUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(StockProductViewChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StockProductViewChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stockProductViewFormService = TestBed.inject(StockProductViewChadFormService);
    stockProductViewService = TestBed.inject(StockProductViewChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const stockProductView: IStockProductViewChad = { id: 456 };

      activatedRoute.data = of({ stockProductView });
      comp.ngOnInit();

      expect(comp.stockProductView).toEqual(stockProductView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductViewChad>>();
      const stockProductView = { id: 123 };
      jest.spyOn(stockProductViewFormService, 'getStockProductViewChad').mockReturnValue(stockProductView);
      jest.spyOn(stockProductViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProductView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stockProductView }));
      saveSubject.complete();

      // THEN
      expect(stockProductViewFormService.getStockProductViewChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stockProductViewService.update).toHaveBeenCalledWith(expect.objectContaining(stockProductView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductViewChad>>();
      const stockProductView = { id: 123 };
      jest.spyOn(stockProductViewFormService, 'getStockProductViewChad').mockReturnValue({ id: null });
      jest.spyOn(stockProductViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProductView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stockProductView }));
      saveSubject.complete();

      // THEN
      expect(stockProductViewFormService.getStockProductViewChad).toHaveBeenCalled();
      expect(stockProductViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductViewChad>>();
      const stockProductView = { id: 123 };
      jest.spyOn(stockProductViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProductView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stockProductViewService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
