local key = KEYS[1];
local limitNum = ARGV[1];
local expTime = ARGV[2];
local afterval = redis.call('incr', key);
if afterval == 1 then
redis.call('expire', key, tonumber(expTime))
return 1;
end
if  afterval > tonumber(limitNum)  then
return 0;
end
return 1;