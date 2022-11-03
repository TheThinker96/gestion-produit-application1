import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStockProductChad } from '../stock-product-chad.model';

@Component({
  selector: 'jhi-stock-product-chad-detail',
  templateUrl: './stock-product-chad-detail.component.html',
})
export class StockProductChadDetailComponent implements OnInit {
  stockProduct: IStockProductChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ stockProduct }) => {
      this.stockProduct = stockProduct;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
