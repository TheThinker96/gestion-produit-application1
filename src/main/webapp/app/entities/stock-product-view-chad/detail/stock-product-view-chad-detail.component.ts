import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockProductViewChad } from '../stock-product-view-chad.model';

@Component({
  selector: 'jhi-stock-product-view-chad-detail',
  templateUrl: './stock-product-view-chad-detail.component.html',
})
export class StockProductViewChadDetailComponent implements OnInit {
  stockProductView: IStockProductViewChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockProductView }) => {
      this.stockProductView = stockProductView;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
