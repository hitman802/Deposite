select users0_.users_id as users_id1_6_0_, users0_.users_email as users_em2_6_0_, users0_.users_name
                        as users_na3_6_0_, users0_.users_password as users_pa4_6_0_, deposits1_.deposite_user as deposite9_1_1_,
  deposits1_.deposite_id as deposite1_1_1_, deposits1_.deposite_id as deposite1_1_2_, deposits1_.deposite_currency
  as deposite8_1_2_, deposits1_.deposite_rate as deposite2_1_2_, deposits1_.deposite_date_end
  as deposite3_1_2_, deposits1_.deposite_name as deposite4_1_2_, deposits1_.deposite_date_start
  as deposite5_1_2_, deposits1_.deposite_sum as deposite6_1_2_, deposits1_.deposite_tax_on_percent
  as deposite7_1_2_, deposits1_.deposite_user as deposite9_1_2_, currency2_.currency_id as currency1_0_3_,
  currency2_.currency_name as currency2_0_3_, replenishm3_.replenishment_deposite as replenis5_4_4_,
  replenishm3_.replenishment_id as replenis1_4_4_, replenishm3_.replenishment_id as replenis1_4_5_,
  replenishm3_.replenishment_currency as replenis4_4_5_, replenishm3_.replenishment_date as replenis2_4_5_,
  replenishm3_.replenishment_deposite as replenis5_4_5_, replenishm3_.replenishment_sum as replenis3_4_5_,
  currency4_.currency_id as currency1_0_6_, currency4_.currency_name as currency2_0_6_, roles5_.usersrole_user
  as usersrol1_7_7_, role6_.role_id as usersrol2_7_7_, role6_.role_id as role_id1_5_8_, role6_.role_name
  as role_nam2_5_8_ from Users users0_ left outer join Deposite deposits1_ on users0_.users_id=deposits1_.deposite_user
  left outer join Currency currency2_ on deposits1_.deposite_currency=currency2_.currency_id
  left outer join Replenishment replenishm3_ on deposits1_.deposite_id=replenishm3_.replenishment_deposite
  left outer join Currency currency4_ on replenishm3_.replenishment_currency=currency4_.currency_id
  left outer join usersrole roles5_ on users0_.users_id=roles5_.usersrole_user left outer join
  Role role6_ on roles5_.usersrole_role=role6_.role_id where users0_.users_id=1