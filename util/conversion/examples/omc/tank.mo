model Tank
  parameter Real d = 0.5        "drain rate / 'size of hole'";
  parameter Real f = 1.0        "fill rate in l/s (constant)";
  input Boolean v(start = true) "Valve closed / open";
  output Real l(start = 5)      "water level";
  Real inFlow                   "Current fill rate";
equation
  der(l) = inFlow - d * l;
  if v then inFlow = f; else inFlow = 0.0; end if;
end Tank;
