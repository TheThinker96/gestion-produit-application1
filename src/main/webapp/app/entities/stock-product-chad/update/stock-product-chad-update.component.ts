import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { StockProductChadFormService, StockProductChadFormGroup } from './stock-product-chad-form.service';
import { IStockProductChad } from '../stock-product-chad.model';
import { StockProductChadService } from '../service/stock-product-chad.service';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';

@Component({
  selector: 'jhi-stock-product-chad-update',
  templateUrl: './stock-product-chad-update.component.html',
})
export class StockProductChadUpdateComponent implements OnInit {
  isSaving = false;
  stockProduct: IStockProductChad | null = null;

  productsSharedCollection: IProductChad[] = [];

  editForm: StockProductChadFormGroup = this.stockProductFormService.createStockProductChadFormGroup();

  constructor(
    protected stockProductService: StockProductChadService,
    protected stockProductFormService: StockProductChadFormService,
    protected productService: ProductChadService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareProductChad = (o1: IProductChad | null, o2: IProductChad | null): boolean => this.productService.compareProductChad(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockProduct }) => {
      this.stockProduct = stockProduct;
      if (stockProduct) {
        this.updateForm(stockProduct);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockProduct = this.stockProductFormService.getStockProductChad(this.editForm);
    if (stockProduct.id !== null) {
      this.subscribeToSaveResponse(this.stockProductService.update(stockProduct));
    } else {
      this.subscribeToSaveResponse(this.stockProductService.create(stockProduct));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockProductChad>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(stockProduct: IStockProductChad): void {
    this.stockProduct = stockProduct;
    this.stockProductFormService.resetForm(this.editForm, stockProduct);

    this.productsSharedCollection = this.productService.addProductChadToCollectionIfMissing<IProductChad>(
      this.productsSharedCollection,
      stockProduct.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProductChad[]>) => res.body ?? []))
      .pipe(
        map((products: IProductChad[]) =>
          this.productService.addProductChadToCollectionIfMissing<IProductChad>(products, this.stockProduct?.product)
        )
      )
      .subscribe((products: IProductChad[]) => (this.productsSharedCollection = products));
  }
}
