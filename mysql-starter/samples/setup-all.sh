echo "Setup All Sample Databases"
SAMPLES_HOME=$(cd $(dirname $0) && pwd)
echo "SAMPLES_HOME=$SAMPLES_HOME"

MYSQL_CMD="mysql -u root"

echo "Create zemiandb"
pushd $SAMPLES_HOME/../zemiandb
$MYSQL_CMD < init.sql
$MYSQL_CMD zemiandb < test.sql
popd

echo "Create world"
echo 'create database if not exists world;' | $MYSQL_CMD
pushd $SAMPLES_HOME
$MYSQL_CMD world < world-mysql-8.0.15.sql
popd

echo "Create sakila"
echo 'create database if not exists sakila;' | $MYSQL_CMD
pushd $SAMPLES_HOME
$MYSQL_CMD sakila < sakila-mysql-8.0.15.sql
popd

# NOTE: The sql script will auto create employees DB!
echo "Create employee"
pushd $SAMPLES_HOME/employees
$MYSQL_CMD < employees.sql
popd
