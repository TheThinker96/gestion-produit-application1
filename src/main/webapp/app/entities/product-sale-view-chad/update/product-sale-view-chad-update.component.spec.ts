import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProductSaleViewChadFormService } from './product-sale-view-chad-form.service';
import { ProductSaleViewChadService } from '../service/product-sale-view-chad.service';
import { IProductSaleViewChad } from '../product-sale-view-chad.model';

import { ProductSaleViewChadUpdateComponent } from './product-sale-view-chad-update.component';

describe('ProductSaleViewChad Management Update Component', () => {
  let comp: ProductSaleViewChadUpdateComponent;
  let fixture: ComponentFixture<ProductSaleViewChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let productSaleViewFormService: ProductSaleViewChadFormService;
  let productSaleViewService: ProductSaleViewChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProductSaleViewChadUpdateComponent],
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
      .overrideTemplate(ProductSaleViewChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProductSaleViewChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    productSaleViewFormService = TestBed.inject(ProductSaleViewChadFormService);
    productSaleViewService = TestBed.inject(ProductSaleViewChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const productSaleView: IProductSaleViewChad = { id: 456 };

      activatedRoute.data = of({ productSaleView });
      comp.ngOnInit();

      expect(comp.productSaleView).toEqual(productSaleView);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleViewChad>>();
      const productSaleView = { id: 123 };
      jest.spyOn(productSaleViewFormService, 'getProductSaleViewChad').mockReturnValue(productSaleView);
      jest.spyOn(productSaleViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSaleView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSaleView }));
      saveSubject.complete();

      // THEN
      expect(productSaleViewFormService.getProductSaleViewChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(productSaleViewService.update).toHaveBeenCalledWith(expect.objectContaining(productSaleView));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleViewChad>>();
      const productSaleView = { id: 123 };
      jest.spyOn(productSaleViewFormService, 'getProductSaleViewChad').mockReturnValue({ id: null });
      jest.spyOn(productSaleViewService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSaleView: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: productSaleView }));
      saveSubject.complete();

      // THEN
      expect(productSaleViewFormService.getProductSaleViewChad).toHaveBeenCalled();
      expect(productSaleViewService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProductSaleViewChad>>();
      const productSaleView = { id: 123 };
      jest.spyOn(productSaleViewService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ productSaleView });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(productSaleViewService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
