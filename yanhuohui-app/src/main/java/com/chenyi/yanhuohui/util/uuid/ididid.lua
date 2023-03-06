---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by 陈义.
--- DateTime: 2023/3/3 15:03
---
-- moudle tag
local tag = KEYS[1];
if tag == nil then
    tag = 'default';
end
-- if user do not pass shardId, default partition is 0.
local partition
if KEYS[2] == nil then
    partition = 0;
else
    partition = KEYS[2] % 4096;
end
local seqKey = 'idgenerator_' .. tag .. '_' .. partition;
local step = 1;
local count;
repeat
    count = tonumber(redis.call('INCRBY', seqKey, step));
until count < (1024 - step)
-- count how many seq are generated in one millisecond
if count == step then
    redis.call('PEXPIRE', seqKey, 1);
end
local now = redis.call('TIME');
-- second, microSecond, partition, seq
return { tonumber(now[1]), tonumber(now[2]), partition, count }

