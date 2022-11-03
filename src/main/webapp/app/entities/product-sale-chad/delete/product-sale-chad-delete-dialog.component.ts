import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSaleChad } from '../product-sale-chad.model';
import { ProductSaleChadService } from '../service/product-sale-chad.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './product-sale-chad-delete-dialog.component.html',
})
export class ProductSaleChadDeleteDialogComponent {
  productSale?: IProductSaleChad;

  constructor(protected productSaleService: ProductSaleChadService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productSaleService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
