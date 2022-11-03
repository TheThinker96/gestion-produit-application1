import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProductTransactionViewChadFormService, ProductTransactionViewChadFormGroup } from './product-transaction-view-chad-form.service';
import { IProductTransactionViewChad } from '../product-transaction-view-chad.model';
import { ProductTransactionViewChadService } from '../service/product-transaction-view-chad.service';
import { TypeTransaction } from 'app/entities/enumerations/type-transaction.model';

@Component({
  selector: 'jhi-product-transaction-view-chad-update',
  templateUrl: './product-transaction-view-chad-update.component.html',
})
export class ProductTransactionViewChadUpdateComponent implements OnInit {
  isSaving = false;
  productTransactionView: IProductTransactionViewChad | null = null;
  typeTransactionValues = Object.keys(TypeTransaction);

  editForm: ProductTransactionViewChadFormGroup = this.productTransactionViewFormService.createProductTransactionViewChadFormGroup();

  constructor(
    protected productTransactionViewService: ProductTransactionViewChadService,
    protected productTransactionViewFormService: ProductTransactionViewChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransactionView }) => {
      this.productTransactionView = productTransactionView;
      if (productTransactionView) {
        this.updateForm(productTransactionView);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productTransactionView = this.productTransactionViewFormService.getProductTransactionViewChad(this.editForm);
    if (productTransactionView.id !== null) {
      this.subscribeToSaveResponse(this.productTransactionViewService.update(productTransactionView));
    } else {
      this.subscribeToSaveResponse(this.productTransactionViewService.create(productTransactionView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductTransactionViewChad>>): void {
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

  protected updateForm(productTransactionView: IProductTransactionViewChad): void {
    this.productTransactionView = productTransactionView;
    this.productTransactionViewFormService.resetForm(this.editForm, productTransactionView);
  }
}
