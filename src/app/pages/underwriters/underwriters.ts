export class UnderwriterRegister {
  constructor(
    public roleId: number,
    public status: boolean,
    public firstName: string,
    public lastName: string,
    public gender: string,
    public email: string,
    public password: string,
    public phoneNo: number,
    public aadharNo: string,
    public education: string,
    public dateOfBirth: string,
    public annualIncome:number
  ) {}
}
export interface IUnderwriter {
  userId: number;
  roleId: number;
  firstName: string;
  lastName: string;
  gender: string;
  email: string;
  phoneNo: number;
  dateOfBirth: string;
  aadharNo: string;
  education: string;
  createdDate: string;
  modifiedDate: string;
  annualIncome:number;
}
