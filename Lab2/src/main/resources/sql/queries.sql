select p.id, p.function_id, p.y, p.x
from labs.public.points p,
     labs.public.functions f
where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linear')
  and p.y = 50.0;

select p.id, p.function_id, p.y, p.x
from labs.public.points p,
     labs.public.functions f
where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linear')
  and p.y = 1.0;

select p.id, p.function_id, p.y, p.x
from labs.public.points p,
     labs.public.functions f
where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linea')
  and p.y = 1.0;

select p.id, p.function_id, p.y, p.x
from labs.public.points p,
     labs.public.functions f
where p.function_id = (select t.id from labs.public.functions t where t.function_type = 'linea')
  and p.y = 50.0;

select *
from labs.public.functions
where function_type = 'linear';

select *
from labs.public.functions
where function_type = 'tip';

select *
from labs.public.functions
where function_type = 'linea';

select *
from labs.public.functions
where function_type = 'tips';