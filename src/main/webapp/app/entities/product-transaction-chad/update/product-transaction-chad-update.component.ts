import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProductTransactionChadFormService, ProductTransactionChadFormGroup } from './product-transaction-chad-form.service';
import { IProductTransactionChad } from '../product-transaction-chad.model';
import { ProductTransactionChadService } from '../service/product-transaction-chad.service';
import { IStockProductChad } from 'app/entities/stock-product-chad/stock-product-chad.model';
import { StockProductChadService } from 'app/entities/stock-product-chad/service/stock-product-chad.service';
import { IProductChad } from 'app/entities/product-chad/product-chad.model';
import { ProductChadService } from 'app/entities/product-chad/service/product-chad.service';
import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

@Component({
  selector: 'jhi-product-transaction-chad-update',
  templateUrl: './product-transaction-chad-update.component.html',
})
export class ProductTransactionChadUpdateComponent implements OnInit {
  isSaving = false;
  productTransaction: IProductTransactionChad | null = null;
  typeTransactionValues = Object.keys(TypeTransaction);

  stockProductsSharedCollection: IStockProductChad[] = [];
  productsSharedCollection: IProductChad[] = [];

  editForm: ProductTransactionChadFormGroup = this.productTransactionFormService.createProductTransactionChadFormGroup();

  constructor(
    protected productTransactionService: ProductTransactionChadService,
    protected productTransactionFormService: ProductTransactionChadFormService,
    protected stockProductService: StockProductChadService,
    protected productService: ProductChadService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareStockProductChad = (o1: IStockProductChad | null, o2: IStockProductChad | null): boolean =>
    this.stockProductService.compareStockProductChad(o1, o2);

  compareProductChad = (o1: IProductChad | null, o2: IProductChad | null): boolean => this.productService.compareProductChad(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransaction }) => {
      this.productTransaction = productTransaction;
      if (productTransaction) {
        this.updateForm(productTransaction);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productTransaction = this.productTransactionFormService.getProductTransactionChad(this.editForm);
    if (productTransaction.id !== null) {
      this.subscribeToSaveResponse(this.productTransactionService.update(productTransaction));
    } else {
      this.subscribeToSaveResponse(this.productTransactionService.create(productTransaction));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTransactionChad>>): void {
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

  protected updateForm(productTransaction: IProductTransactionChad): void {
    this.productTransaction = productTransaction;
    this.productTransactionFormService.resetForm(this.editForm, productTransaction);

    this.stockProductsSharedCollection = this.stockProductService.addStockProductChadToCollectionIfMissing<IStockProductChad>(
      this.stockProductsSharedCollection,
      productTransaction.stockProduct
    );
    this.productsSharedCollection = this.productService.addProductChadToCollectionIfMissing<IProductChad>(
      this.productsSharedCollection,
      productTransaction.product
    );
  }

  protected loadRelationshipsOptions(): void {
    this.stockProductService
      .query()
      .pipe(map((res: HttpResponse<IStockProductChad[]>) => res.body ?? []))
      .pipe(
        map((stockProducts: IStockProductChad[]) =>
          this.stockProductService.addStockProductChadToCollectionIfMissing<IStockProductChad>(
            stockProducts,
            this.productTransaction?.stockProduct
          )
        )
      )
      .subscribe((stockProducts: IStockProductChad[]) => (this.stockProductsSharedCollection = stockProducts));

    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProductChad[]>) => res.body ?? []))
      .pipe(
        map((products: IProductChad[]) =>
          this.productService.addProductChadToCollectionIfMissing<IProductChad>(products, this.productTransaction?.product)
        )
      )
      .subscribe((products: IProductChad[]) => (this.productsSharedCollection = products));
  }
}
