import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { StockProductViewChadFormService, StockProductViewChadFormGroup } from './stock-product-view-chad-form.service';
import { IStockProductViewChad } from '../stock-product-view-chad.model';
import { StockProductViewChadService } from '../service/stock-product-view-chad.service';

@Component({
  selector: 'jhi-stock-product-view-chad-update',
  templateUrl: './stock-product-view-chad-update.component.html',
})
export class StockProductViewChadUpdateComponent implements OnInit {
  isSaving = false;
  stockProductView: IStockProductViewChad | null = null;

  editForm: StockProductViewChadFormGroup = this.stockProductViewFormService.createStockProductViewChadFormGroup();

  constructor(
    protected stockProductViewService: StockProductViewChadService,
    protected stockProductViewFormService: StockProductViewChadFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockProductView }) => {
      this.stockProductView = stockProductView;
      if (stockProductView) {
        this.updateForm(stockProductView);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const stockProductView = this.stockProductViewFormService.getStockProductViewChad(this.editForm);
    if (stockProductView.id !== null) {
      this.subscribeToSaveResponse(this.stockProductViewService.update(stockProductView));
    } else {
      this.subscribeToSaveResponse(this.stockProductViewService.create(stockProductView));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockProductViewChad>>): void {
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

  protected updateForm(stockProductView: IStockProductViewChad): void {
    this.stockProductView = stockProductView;
    this.stockProductViewFormService.resetForm(this.editForm, stockProductView);
  }
}
