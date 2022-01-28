--Indexing for faredistribution Table--
alter table faredistribution add index fd_fdtId_idx(fareDistributionTypeId);

--Indexing for fdtransaction Table--
alter table fdtransaction add index fdt_bId_idx(bookingId);
alter table  fdtransaction add index fdt_dId_idx(driverId);
alter table  fdtransaction add index fdt_fId_idx(franchiseId);
alter table  fdtransaction add index fdt_bksDate_idx(bookingDate);

--Indexing for fdtransactiondetail Table--
alter table  fdtransactiondetail add index fdtd_fdtId_idx(fdTransactionId);
alter table  fdtransactiondetail add index fdtd_fdId_idx(fareDistributionId);
