# mainapp/bridge_models.py

from django.db import models

class Bridge(models.Model):
    name = models.CharField(max_length=200)
    source_chain = models.CharField(max_length=100)
    destination_chain = models.CharField(max_length=100)
    created_at = models.DateTimeField(auto_now_add=True)
    
    def __str__(self):
        return f"{self.name}: {self.source_chain} → {self.destination_chain}"