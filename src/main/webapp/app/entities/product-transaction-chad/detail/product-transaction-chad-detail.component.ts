import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductTransactionChad } from '../product-transaction-chad.model';

@Component({
  selector: 'jhi-product-transaction-chad-detail',
  templateUrl: './product-transaction-chad-detail.component.html',
})
export class ProductTransactionChadDetailComponent implements OnInit {
  productTransaction: IProductTransactionChad | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productTransaction }) => {
      this.productTransaction = productTransaction;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
