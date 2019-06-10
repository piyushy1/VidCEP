while true;  do nvidia-smi --query-gpu=utilization.gpu,utilization.memory,memory.total,memory.free,memory.used --format=csv >> gpu_utillization.csv; sleep 0.5;  done
