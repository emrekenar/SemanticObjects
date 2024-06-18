% load the simulink model
load_system(model_name);

% set fixed-step solver
set_param(model_name, 'SolverType', 'Fixed-step');

% export to fmu
exportToFMU(model_name, 'FMIVersion', '2.0', 'FMUType', 'CS');