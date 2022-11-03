import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'product-chad',
        data: { pageTitle: 'gestionProduitApplication1App.product.home.title' },
        loadChildren: () => import('./product-chad/product-chad.module').then(m => m.ProductChadModule),
      },
      {
        path: 'stock-product-chad',
        data: { pageTitle: 'gestionProduitApplication1App.stockProduct.home.title' },
        loadChildren: () => import('./stock-product-chad/stock-product-chad.module').then(m => m.StockProductChadModule),
      },
      {
        path: 'product-sale-chad',
        data: { pageTitle: 'gestionProduitApplication1App.productSale.home.title' },
        loadChildren: () => import('./product-sale-chad/product-sale-chad.module').then(m => m.ProductSaleChadModule),
      },
      {
        path: 'product-transaction-chad',
        data: { pageTitle: 'gestionProduitApplication1App.productTransaction.home.title' },
        loadChildren: () => import('./product-transaction-chad/product-transaction-chad.module').then(m => m.ProductTransactionChadModule),
      },
      {
        path: 'stock-product-view-chad',
        data: { pageTitle: 'gestionProduitApplication1App.stockProductView.home.title' },
        loadChildren: () => import('./stock-product-view-chad/stock-product-view-chad.module').then(m => m.StockProductViewChadModule),
      },
      {
        path: 'product-sale-view-chad',
        data: { pageTitle: 'gestionProduitApplication1App.productSaleView.home.title' },
        loadChildren: () => import('./product-sale-view-chad/product-sale-view-chad.module').then(m => m.ProductSaleViewChadModule),
      },
      {
        path: 'product-transaction-view-chad',
        data: { pageTitle: 'gestionProduitApplication1App.productTransactionView.home.title' },
        loadChildren: () =>
          import('./product-transaction-view-chad/product-transaction-view-chad.module').then(m => m.ProductTransactionViewChadModule),
      },
      {
        path: 'user-sale-account-chad',
        data: { pageTitle: 'gestionProduitApplication1App.userSaleAccount.home.title' },
        loadChildren: () => import('./user-sale-account-chad/user-sale-account-chad.module').then(m => m.UserSaleAccountChadModule),
      },
      {
        path: 'user-cash-back-chad',
        data: { pageTitle: 'gestionProduitApplication1App.userCashBack.home.title' },
        loadChildren: () => import('./user-cash-back-chad/user-cash-back-chad.module').then(m => m.UserCashBackChadModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
