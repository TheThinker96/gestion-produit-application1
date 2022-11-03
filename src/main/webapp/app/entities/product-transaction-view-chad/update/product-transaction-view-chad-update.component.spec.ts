import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductTransactionViewChadFormService } from './product-transaction-view-chad-form.service';
import { ProductTransactionViewChadService } from '../service/product-transaction-view-chad.service';
import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';

import { ProductTransactionViewChadUpdateComponent } from './product-transaction-view-chad-update.component';

describe('ProductTransactionViewChad Management Update Component', () => {
  let comp: ProductTransactionViewChadUpdateComponent;
  let fixture: ComponentFixture<ProductTransactionViewChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productTransactionViewFormService: ProductTransactionViewChadFormService;
  let productTransactionViewService: ProductTransactionViewChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductTransactionViewChadUpdateComponent],
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
      .overrideTemplate(ProductTransactionViewChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductTransactionViewChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productTransactionViewFormService = TestBed.inject(ProductTransactionViewChadFormService);
    productTransactionViewService = TestBed.inject(ProductTransactionViewChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productTransactionView: IProductTransactionViewChad = { id: 456 };

      activatedRoute.data = of({ productTransactionView });
      comp.ngOnInit();

      expect(comp.productTransactionView).toEqual(productTransactionView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionViewChad>>();
      const productTransactionView = { id: 123 };
      jest.spyOn(productTransactionViewFormService, 'getProductTransactionViewChad').mockReturnValue(productTransactionView);
      jest.spyOn(productTransactionViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransactionView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransactionView }));
      saveSubject.complete();

      // THEN
      expect(productTransactionViewFormService.getProductTransactionViewChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productTransactionViewService.update).toHaveBeenCalledWith(expect.objectContaining(productTransactionView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionViewChad>>();
      const productTransactionView = { id: 123 };
      jest.spyOn(productTransactionViewFormService, 'getProductTransactionViewChad').mockReturnValue({ id: null });
      jest.spyOn(productTransactionViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransactionView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productTransactionView }));
      saveSubject.complete();

      // THEN
      expect(productTransactionViewFormService.getProductTransactionViewChad).toHaveBeenCalled();
      expect(productTransactionViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductTransactionViewChad>>();
      const productTransactionView = { id: 123 };
      jest.spyOn(productTransactionViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productTransactionView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productTransactionViewService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
