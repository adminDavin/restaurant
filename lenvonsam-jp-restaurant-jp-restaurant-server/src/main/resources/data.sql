-- 前台新闻列表
create or replace view v_news_ft_list as
select id, publish_date, news_title, news_property, merchant_code, platform_code from t_sys_news where status = 1 and del_flag=0;
