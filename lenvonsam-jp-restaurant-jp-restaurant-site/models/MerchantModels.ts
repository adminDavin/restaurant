export interface FileDTO {
  id?: string
  fileName?:string
  fileUrl: string
  fileBusiType?:string
  fileBusiCode?:string
  fileType: number
  fileCloudName: string
}
export interface MerchantDTO {
  id?: string
  merchantCode?: string
  merchantName: string
  email: string
  thumbUrl?: string
  phone: string
  address: string
  uberSite?: string
  facebookSite?:string
  twSite?: string
  insSite?: string
  lineSite?: string
  mapUrl?: string
  infrastructureMark?: string
  intro: string
  openTimeInfo?: string
  busiType?: string
  gallery?: Array<FileDTO>
  thumbCompt?: Array<any>
  galleryCompt?: Array<any>
}

export interface MerchantAppiontDTO {
  id?: string
  merchantCode: string
  merchantName: string
  appointTime: string
  appointType: number
}

export interface MerchantProductDTO {
  id?: string
  merchantCode: string
  merchantName: string
  productCode?: string
  productTitle: string
  productSubTitle: string
  productDesc?: string
  productCoverUrl: string
  productDescUrl: string
  productType: number
  status?: number
  coverCompt?: Array<any>
  descCompt?: Array<any>
}

export interface MerchantCardDTO {
  id?: string
  busiCode: string
  busiName: string
  busiType: string
  title: string
  content: string
}