--select * from all_pipe ap ;
--select * from wtl_pipe_lm;
--
--select
--        layer_id,
--        ftr_cde,
--        ftr_idn,
--        replace(A.mdl_pth,
--        '.3ds',
--        '') mdl_pth,
--        std_nam,
--        ist_ymd,
--        mng_cde,
--        dep,
--        std_dep,
--        low_dep,
--        hgh_dep,
--        MID_LOW_HGH_DEP,
--        beg_dep,
--        end_dep,
--        MID_BEG_END_DEP   ,
--        ST_AsText(geom) geom,
--        cross_point  
--    from
--        (   select
--            mdl_pth,
--            cross_point,
--            cross_point2,
--            substr(cross_point2,
--            1,
--            position(' ' in cross_point2)) cross_point_x,
--            substr(cross_point2,
--            position(' ' in cross_point2)+1) cross_point_ex   
--        from
--            (    select
--                mdl_pth,
--                cross_point,
--                replace(replace(cross_point,
--                'POINT Z (',
--                ''),
--                ')',
--                '') cross_point2    
--            from
--                (     SELECT
--                    mdl_pth      ,
--                    ST_AsText(ST_Intersection(st_geomfromtext('LINESTRING( 211995.00299272366 358927.23101782415,211979.63738808708 358896.9435379585 )',
--                    5186),
--                    geom)) as cross_point      
--                FROM
--                    all_pipe      
--                WHERE
--                    ST_Intersects( st_geomfromtext('LINESTRING( 211995.00299272366 358927.23101782415,211979.63738808708 358896.9435379585 )', 5186), geom )    ) Z   ) Y  ) Z,
--            all_pipe A  
--        where
--            Z.mdl_pth = A.mdl_pth  
--        order by
--            to_number(cross_point_x,
--            '999999G99999999999') asc ,
--            to_number(substr(cross_point_ex,
--            1,
--            position(' ' in cross_point_ex)-1),
--            '999999G9999999999') desc  
--            ;

-- all_pipe vs WTL_PIPE_LM (LOW_DEP , HGH_DEP) : 상수관로 
select count(*) from WTL_PIPE_LM a where LOW_DEP > 0 or HGH_DEP > 0; -- // 3,353
select count(*) from all_pipe a where LOW_DEP > 0 or HGH_DEP > 0 ;

-- all_pipe vs UFL_PIPE_LM (STD_DEP) : 광역상수관
select count(*) from UFL_PIPE_LM a where STD_DEP > 0; -- // 19
select count(*) from all_pipe a where STD_DEP > 0;

-- all_pipe vs UFL_BPIP_LM (STD_DEP) : 전력지중관로
select count(*) from UFL_BPIP_LM a where STD_DEP > 0;  -- // 30413
select count(*) from all_pipe a where STD_DEP > 0;

-- all_pipe vs UFL_GPIP_LM (STD_DEP) : 천연가스배관 --> 
select count(*) from UFL_GPIP_LM a where STD_DEP > 0; -- // 0 : ( STD_DIP	관경, STD_LEN	 길이 ) < 값 존재
select count(*) from all_pipe a where STD_DEP > 0;

-- all_pipe vs SWL_PIPE_LM (BEG_DEP, END_DEP) : 하수관거 --> 
select count(*) from SWL_PIPE_LM a where BEG_DEP > 0 or END_DEP > 0; -- // 52,307
select count(*) from all_pipe a where BEG_DEP > 0 or END_DEP > 0;

-- all_pipe vs UFL_KPIP_LS (BEG_DEP, END_DEP) : 천연가스배관 --> 
select count(*) from UFL_KPIP_LS a where STD_DEP > 0; -- // 16,234
select count(*) from all_pipe a where STD_DEP > 0;

select * from all_pipe where std_dep > 0;

select LOW_DEP from  WTL_PIPE_LM where LOW_DEP > 0;

select count(*) from WTL_PIPE_LM a; -- 22122

select
count(distinct ( a.mdl_pth)) from WTL_PIPE_LM a
; -- 22122


SELECT st_geogfromtext(
'MULTILINESTRING ZM ((212043.806116 358932.46326600004 28.94999999999709 -1.797693134862316e+308,212008.91643600003 358926.493796 28.39999999999418 -1.797693134862316e+308,211987.46262600005 358922.915486 27.85000000000582 -1.797693134862316e+308))'
		)
	;

select
--st_astext(
--st_multi(
st_geometryfromtext('MULTILINESTRING ZM ((212043.806116 358932.46326600004 28.94999999999709 -1.797693134862316e+308,212008.91643600003 358926.493796 28.39999999999418 -1.797693134862316e+308,211987.46262600005 358922.915486 27.85000000000582 -1.797693134862316e+308))',5186)
--)
--)
;
