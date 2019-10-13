/*Data for the table `b_tran_userid_check` 需要从报文头去userId的交易*/
delete from `b_tran_userid_check`;

insert  into `b_tran_userid_check`(`tran_url`,`body_field`) values ('/people/loginout','userId');
insert  into `b_tran_userid_check`(`tran_url`,`body_field`) values ('/people/updatePass','userId');
insert  into `b_tran_userid_check`(`tran_url`,`body_field`) values ('/manager/orginfo/queryOrgListByPid','userId');
insert  into `b_tran_userid_check`(`tran_url`,`body_field`) values ('/manager/menu/loadSystemModule','userId');
