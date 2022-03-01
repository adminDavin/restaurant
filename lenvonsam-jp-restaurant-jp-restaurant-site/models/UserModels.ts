export interface UserDTO {
  id?: string
  userCode?: string
  userName: string
  userPwd?: string
  nickname?: string
  loginName: string
  status: number
}

export interface UserAppointDTO {
  id?: string
  userCode?: string
  userName: string
  merchantCode: string
  merchantName: string
  appointTime: string
  appointRange: string
  receiveEmail: string
  appointPhone: string
  adultCount: number
  childCount: number
  productCode: string
  productTitle: string
  remark: string
}