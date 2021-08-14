import { TestBed } from "@angular/core/testing";

import { UnderwritersService } from "./underwriters.service";

describe("UnderwritersService", () => {
  let service: UnderwritersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnderwritersService);
  });

  it("should be created", () => {
    expect(service).toBeTruthy();
  });
});
