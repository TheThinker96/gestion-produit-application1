import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StockProductChadFormService } from './stock-product-chad-form.service';
import { StockProductChadService } from '../service/stock-product-chad.service';
import { IStockProductChad } from '../stock-product-chad.model';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';

import { StockProductChadUpdateComponent } from './stock-product-chad-update.component';

describe('StockProductChad Management Update Component', () => {
  let comp: StockProductChadUpdateComponent;
  let fixture: ComponentFixture<StockProductChadUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let stockProductFormService: StockProductChadFormService;
  let stockProductService: StockProductChadService;
  let productService: ProductChadService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StockProductChadUpdateComponent],
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
      .overrideTemplate(StockProductChadUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StockProductChadUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    stockProductFormService = TestBed.inject(StockProductChadFormService);
    stockProductService = TestBed.inject(StockProductChadService);
    productService = TestBed.inject(ProductChadService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call ProductChad query and add missing value', () => {
      const stockProduct: IStockProductChad = { id: 456 };
      const product: IProductChad = { id: 87794 };
      stockProduct.product = product;

      const productCollection: IProductChad[] = [{ id: 48112 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProductChads = [product];
      const expectedCollection: IProductChad[] = [...additionalProductChads, ...productCollection];
      jest.spyOn(productService, 'addProductChadToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ stockProduct });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductChadToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProductChads.map(expect.objectContaining)
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const stockProduct: IStockProductChad = { id: 456 };
      const product: IProductChad = { id: 4673 };
      stockProduct.product = product;

      activatedRoute.data = of({ stockProduct });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.stockProduct).toEqual(stockProduct);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductChad>>();
      const stockProduct = { id: 123 };
      jest.spyOn(stockProductFormService, 'getStockProductChad').mockReturnValue(stockProduct);
      jest.spyOn(stockProductService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stockProduct }));
      saveSubject.complete();

      // THEN
      expect(stockProductFormService.getStockProductChad).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(stockProductService.update).toHaveBeenCalledWith(expect.objectContaining(stockProduct));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductChad>>();
      const stockProduct = { id: 123 };
      jest.spyOn(stockProductFormService, 'getStockProductChad').mockReturnValue({ id: null });
      jest.spyOn(stockProductService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProduct: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: stockProduct }));
      saveSubject.complete();

      // THEN
      expect(stockProductFormService.getStockProductChad).toHaveBeenCalled();
      expect(stockProductService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IStockProductChad>>();
      const stockProduct = { id: 123 };
      jest.spyOn(stockProductService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ stockProduct });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(stockProductService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProductChad', () => {
      it('Should forward to productService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(productService, 'compareProductChad');
        comp.compareProductChad(entity, entity2);
        expect(productService.compareProductChad).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
