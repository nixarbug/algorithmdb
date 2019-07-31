export interface IFunctionClass {
  id?: number;
  name?: string;
  formula?: any;
  relativeOrder?: number;
}

export class FunctionClass implements IFunctionClass {
  constructor(public id?: number, public name?: string, public formula?: any, public relativeOrder?: number) {}
}
