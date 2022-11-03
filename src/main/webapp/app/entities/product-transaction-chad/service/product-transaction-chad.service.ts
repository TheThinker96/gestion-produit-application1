import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductTransactionChad, NewProductTransactionChad } from '../product-transaction-chad.model';

export type PartialUpdateProductTransactionChad = Partial<IProductTransactionChad> & Pick<IProductTransactionChad, 'id'>;

export type EntityResponseType = HttpResponse<IProductTransactionChad>;
export type EntityArrayResponseType = HttpResponse<IProductTransactionChad[]>;

@Injectable({ providedIn: 'root' })
export class ProductTransactionChadService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-transactions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productTransaction: NewProductTransactionChad): Observable<EntityResponseType> {
    return this.http.post<IProductTransactionChad>(this.resourceUrl, productTransaction, { observe: 'response' });
  }

  update(productTransaction: IProductTransactionChad): Observable<EntityResponseType> {
    return this.http.put<IProductTransactionChad>(
      `${this.resourceUrl}/${this.getProductTransactionChadIdentifier(productTransaction)}`,
      productTransaction,
      { observe: 'response' }
    );
  }

  partialUpdate(productTransaction: PartialUpdateProductTransactionChad): Observable<EntityResponseType> {
    return this.http.patch<IProductTransactionChad>(
      `${this.resourceUrl}/${this.getProductTransactionChadIdentifier(productTransaction)}`,
      productTransaction,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductTransactionChad>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductTransactionChad[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProductTransactionChadIdentifier(productTransaction: Pick<IProductTransactionChad, 'id'>): number {
    return productTransaction.id;
  }

  compareProductTransactionChad(o1: Pick<IProductTransactionChad, 'id'> | null, o2: Pick<IProductTransactionChad, 'id'> | null): boolean {
    return o1 && o2 ? this.getProductTransactionChadIdentifier(o1) === this.getProductTransactionChadIdentifier(o2) : o1 === o2;
  }

  addProductTransactionChadToCollectionIfMissing<Type extends Pick<IProductTransactionChad, 'id'>>(
    productTransactionCollection: Type[],
    ...productTransactionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const productTransactions: Type[] = productTransactionsToCheck.filter(isPresent);
    if (productTransactions.length > 0) {
      const productTransactionCollectionIdentifiers = productTransactionCollection.map(
        productTransactionItem => this.getProductTransactionChadIdentifier(productTransactionItem)!
      );
      const productTransactionsToAdd = productTransactions.filter(productTransactionItem => {
        const productTransactionIdentifier = this.getProductTransactionChadIdentifier(productTransactionItem);
        if (productTransactionCollectionIdentifiers.includes(productTransactionIdentifier)) {
          return false;
        }
        productTransactionCollectionIdentifiers.push(productTransactionIdentifier);
        return true;
      });
      return [...productTransactionsToAdd, ...productTransactionCollection];
    }
    return productTransactionCollection;
  }
}
